# CrunchBase Direct

Dedicated app to browse and search [CrunchBase](http://www.crunchbase.com/) data on Android 4.0.3 and above. You can download the latest version from [Google Play](https://play.google.com/store/apps/details?id=com.github.zyro.crunchbased).

## Build

Requirements:
* JDK 1.6.0+
* Gradle 1.8+
* Properly configured Android SDK API level 18 and Build Tools 18.0.1

That's it! All other libraries are retrieved by Gradle during the build process. All you need is `gradle build`.

## API Key

While the above is enough to compile and run the application, you will need to add your API key to use any feature beyond browsing the home tabs.

1. Register for the CrunchBase Developer API [here](http://developer.crunchbase.com/) and generate an API key. It should only take moments to get your key.

2. Find the `Api` class in package `com.github.zyro.crunchbased.service`. It looks like this:

    ```java
    package com.github.zyro.crunchbased.service;

    public class Api {

        public static final String KEY = "YOUR CRUNCHBASE KEY HERE";

    }
    ```

3. Replace the value of the `KEY` field with your API key.

4. Build the app and away you go!

## Attribution

This project uses the following Android libraries:
* [AndroidAnnotations](https://github.com/excilys/androidannotations)
* [Ion](https://github.com/koush/ion)
* [ActionBar-PullToRefresh](https://github.com/chrisbanes/ActionBar-PullToRefresh)

Some graphical resources generated with:
* [Android Asset Studio](http://android-ui-utils.googlecode.com/hg/asset-studio/dist/index.html)
* [Icon Slayer](http://www.gieson.com/Library/projects/utilities/icon_slayer/)

## License

```
The MIT License (MIT)

Copyright (c) 2013 Andrei Mihu

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
```
