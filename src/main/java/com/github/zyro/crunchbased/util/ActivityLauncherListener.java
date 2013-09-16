package com.github.zyro.crunchbased.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import com.github.zyro.crunchbased.R;
import com.github.zyro.crunchbased.activity.CompanyActivity_;
import com.github.zyro.crunchbased.activity.FinancialOrganizationActivity_;
import com.github.zyro.crunchbased.activity.PersonActivity_;
import com.github.zyro.crunchbased.activity.ProductActivity_;
import com.github.zyro.crunchbased.activity.ServiceProviderActivity_;

/**
 * OnClickListener implementation that launches an internal activity selected
 * based on input parameters. Also allows immediate triggering of the activity.
 */
public class ActivityLauncherListener implements View.OnClickListener {

    /** The activity that acts as the source of the intent. */
    private Activity activity;

    /** The namespace of the item, used to determine target activity. */
    private String namespace;

    /** The permalink identifier to be passed to the target activity. */
    private String permalink;

    /**
     * OnClickListener implementation that launches an internal activity
     *
     * @param activity
     * @param namespace
     * @param permalink
     */
    public ActivityLauncherListener(final Activity activity,
                                    final String namespace,
                                    final String permalink) {
        this.activity = activity;
        this.namespace = namespace;
        this.permalink = permalink;
    }

    /**
     * Listener implementation that will start the configured activity with the
     * configured parameters.
     *
     * @param view The triggering view. Unused but required by interface.
     */
    @Override
    public void onClick(final View view) {
        launchNow();
    }

    /** Start the configured activity with the configured parameters now. */
    public void launchNow() {
        if(namespace.equals("company")) {
            final Intent intent = new Intent(activity, CompanyActivity_.class);
            intent.setData(Uri.parse("http://www.crunchbase.com/company/" + permalink));
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        }
        else if(namespace.equals("financial-organization") ||
                namespace.equals("financial_org")) {
            final Intent intent = new Intent(activity, FinancialOrganizationActivity_.class);
            intent.setData(Uri.parse("http://www.crunchbase.com/financial-organization/" + permalink));
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        }
        else if(namespace.equals("person")) {
            final Intent intent = new Intent(activity, PersonActivity_.class);
            intent.setData(Uri.parse("http://www.crunchbase.com/person/" + permalink));
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        }
        else if(namespace.equals("product")) {
            final Intent intent = new Intent(activity, ProductActivity_.class);
            intent.setData(Uri.parse("http://www.crunchbase.com/product/" + permalink));
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        }
        else if(namespace.equals("service-provider")) {
            final Intent intent = new Intent(activity, ServiceProviderActivity_.class);
            intent.setData(Uri.parse("http://www.crunchbase.com/service-provider/" + permalink));
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        }
        else {
            Toast.makeText(activity, "Unknown entity type: " + namespace, Toast.LENGTH_SHORT).show();
        }
    }

}