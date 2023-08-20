import 'package:flutter/material.dart';
import 'package:watchface_config/watchface_info_provider.dart';
import 'complication_selector_wrapper.dart';
import 'package:wearable_flutter_fragment_application/wearable_fragment_application_observers.dart';
import 'package:wearable_flutter_fragment_application/dismissible_container.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        debugShowCheckedModeBanner: false,
        title: 'Watchface config example',
        theme: ThemeData(),
        navigatorObservers: WearableFragmentApplication().observers,
        home: DismissibleContainer(
            child: WatchFaceInfoProvider(
                builder: (context, watchFaceInfo) => ComplicationSelectorScreenWrapper(
                      uniqueWatchfaceId: watchFaceInfo.fullName,
                    ))));
  }
}
