# Watchface Config

This plugin helps you make your Config Activity for WatchFaces in Flutter.

## Description

The advantages of the plugin is it lets you make different config activities for your watchfaces in Flutter without writing additional code "for each" in your manifest file. On flutter side, you have a provider widget for getting and setting complications, and an another one to determine what watchface the given Flutter application relates to.

The plugin uses [wearable_flutter_fragment_application](https://pub.dev/packages/wearable_flutter_fragment_application) to work, it's necessary for you to use it in your application as well: covered in [Setup](#setup).

2 widgets are provided:
- ComplicationReceiver: You can reach the complications of your watchface and launch the complication selector activity for each directly.
- WatchFaceInfoProvider: It provides you some information about your watchface to be identified uniquely. It has a name parameter to be customized on Android side.

## Examples
### example 
Basic example of how to get and set a static number of complications on your only watchface. 

![Example](https://popovok.github.io/markdown_files/watchface_config/wear_config_example.gif)

### example_multiple_config
It shows you how to make different activities for multiple watchfaces, WatchFaceInfoProvider widget is being used here.

![Multiple Config Example](https://popovok.github.io/markdown_files/watchface_config/wear_config_multiple_config_example.gif)

### example_complex
Dynamic number of complications being used. The number of it is handled on Flutter side, the Android side adjusts the placing of the complications by it. Wearable applications usually DON'T USE this feature, it might be complicated to implement.

|*adding complication*|*removing complication*|
|:--:|:--:|
|![Complex example - Add](https://popovok.github.io/markdown_files/watchface_config/wear_config_complex_add_example.gif)|![Complex example - Remove](https://popovok.github.io/markdown_files/watchface_config/wear_config_complex_remove_example.gif)

## Setup
### Android

Your watchface service in the Manifest must include the following meta-data with a value of **CONFIG_ACTIVITY** to use the provided config activity by the plugin.

```xml
<service
    android:name=".watchface.WatchFaceExample"
    ...>
    <meta-data
        android:name="com.google.android.wearable.watchface.wearableConfigurationAction"
        android:value="CONFIG_ACTIVITY"/>
        ...
</service>
```

To use a custom config activity for some of the watchfaces, you must extend it from ConfigActivity(), and use a custom value eg. **CUSTOM_CONFIG_ACTIVITY** for the meta-data above. This new class also needs to be put into the Manifest.

```xml
<activity
    android:name=".CustomConfigActivity"
    android:exported="true">

    <intent-filter>
        <!-- Same custom value -->
        <action android:name="CUSTOM_CONFIG_ACTIVITY"/>
        <category android:name="com.google.android.wearable.watchface.category.WEARABLE_CONFIGURATION"/>
        <category android:name="android.intent.category.DEFAULT"/>
    </intent-filter>
</activity>
```

### Flutter

Using the wearable_flutter_fragment_application [requires you to add it as a dependency](https://pub.dev/packages/wearable_flutter_fragment_application/install) in your pubspec.yaml. Setting **WearableFragmentApplication().observers** for navigatorObservers is required, setting **DismissibleContainer()** as a child for your MaterialApp is recommended to dismiss the activity, but it is not necessary to use.

```dart
MaterialApp(
        title: 'Watchface config example',
        theme: ThemeData(),
        navigatorObservers: WearableFragmentApplication().observers,
        home: DismissibleContainer(child: Container())
);
```

### Important note
Don't extend the functions of `MainActivity : FlutterAcivity()` (eg. for `MethodChannel`), the app won't use it. You can inherit from the Config Activity to access the FlutterEngine. See more in  [wearable_flutter_fragment_application](https://pub.dev/packages/wearable_flutter_fragment_application).

The finished application don't have to have a MainActivity (so you can delete it from the Manifest and set the application not to have a default activity after you finished working on it), but it can be helpful while debugging the application. The plugin provides default values for the WatchFaceInfoProvider and ComplicationReceiver classes to run it without a watchface. If you have a separate application alongside watchfaces in your project, I highly recommend using the [wearable_flutter_fragment_application](https://pub.dev/packages/wearable_flutter_fragment_application) package for it instead of using the MainActivity.

![Config Debug](https://popovok.github.io/markdown_files/watchface_config/wear_config_debug.png)

## Support
- Min. System requirement is Android 7.1 - Wear OS 2.0

## Tested on
- Moto 360 gen 2 - Android 7.1 - Wear OS 2.0

## TODO: 
- Exchanging Complications
