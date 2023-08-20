import 'package:collection/collection.dart';
import 'package:flutter/material.dart';
import 'package:watchface_config/complication_receiver.dart';
import 'package:watchface_config_example_multiple_config_activity/first_example.dart';
import 'package:watchface_config_example_multiple_config_activity/second_example.dart';
import 'package:wearable_flutter_fragment_application/wearable_fragment_application_observers.dart';
import 'package:wearable_flutter_fragment_application/dismissible_container.dart';

import 'buttons.dart';

@pragma("secondary")
void secondary() {
  runApp(MyApp(primaryColor: Colors.green, child: ComplicationSelectorScreenSecond()));
}

void main() {
  runApp(MyApp(primaryColor: Colors.blue, child: ComplicationSelectorScreenFirst()));
}

class MyApp extends StatelessWidget {
  final Widget child;
  final Color primaryColor;

  const MyApp({required this.primaryColor, required this.child, super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        debugShowCheckedModeBanner: false,
        title: 'Watchface config example',
        theme: ThemeData(primaryColor: primaryColor),
        navigatorObservers: WearableFragmentApplication().observers,
        home: DismissibleContainer(child: child));
  }
}
