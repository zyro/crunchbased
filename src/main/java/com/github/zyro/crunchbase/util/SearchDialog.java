package com.github.zyro.crunchbase.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.zyro.crunchbase.R;
import com.github.zyro.crunchbase.activity.SearchActivity_;

import org.apache.commons.lang3.StringUtils;

/** Helper class to handle search dialog display and functionality. */
public class SearchDialog {

    /** Strictly a static helper class. */
    protected SearchDialog() {}

    /**
     * Create and display the search dialog on top of the given activity. This
     * is a fully self-contained dialog and will handle all behaviour by itself.
     *
     * @param activity The Activity to bind the search dialog to.
     */
    public static void showSearchDialog(final Activity activity) {
        final LayoutInflater inflater = (LayoutInflater)
                activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.search_dialog, null);

        final AlertDialog dialog = new AlertDialog.Builder(activity)
                //.setIcon(R.drawable.ic_action_search_dark)
                //.setTitle(R.string.search_title)
                .setView(view)
                .setPositiveButton(R.string.search,
                        new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog,
                                                final int whichButton) {
                                final String query = ((EditText)
                                        view.findViewById(R.id.searchQuery))
                                        .getText().toString().trim();
                                if(StringUtils.isBlank(query)) {
                                    Toast.makeText(activity,
                                            "Search query cannot be blank.",
                                            Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                final String entity = ((Spinner)
                                        view.findViewById(R.id.searchEntity))
                                        .getSelectedItem().toString();
                                final String field = ((Spinner)
                                        view.findViewById(R.id.searchField))
                                        .getSelectedItem().toString();

                                final Intent intent = new Intent(
                                        activity, SearchActivity_.class);
                                intent.putExtra("searchQuery", query);
                                intent.putExtra("searchEntity", entity);
                                intent.putExtra("searchField", field);
                                activity.startActivity(intent);
                                activity.overridePendingTransition(
                                        R.anim.slide_right_in,
                                        R.anim.slide_left_out);
                            }
                        })
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog,
                                                final int whichButton) {
                                dialog.dismiss();
                            }
                        }).create();

        view.findViewById(R.id.searchQuery).setOnFocusChangeListener(
                new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(final View view,
                                              final boolean hasFocus) {
                        if(hasFocus) {
                            dialog.getWindow().setSoftInputMode(WindowManager.
                                    LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                        }
                    }
                });

        dialog.show();

        // dialog.getButton() returns null if called before dialog.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundResource(
                R.drawable.button_selector);
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setBackgroundResource(
                R.drawable.button_selector);
    }

}