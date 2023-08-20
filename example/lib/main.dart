import 'package:collection/collection.dart';
import 'package:flutter/material.dart';
import 'package:watchface_config/complication_receiver.dart';
import 'package:watchface_config/model/complication_type.dart';
import 'package:wearable_flutter_fragment_application/wearable_fragment_application_observers.dart';
import 'package:wearable_flutter_fragment_application/dismissible_container.dart';

import 'buttons.dart';

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
        home: DismissibleContainer(child: ComplicationSelectorScreen()));
  }
}

class ComplicationSelectorScreen extends StatelessWidget {
  final List<List<ComplicationType>> supportedTypeList =
      List.generate(3, (index) => acceptableComplicationTypesByExample.toList());

  static const acceptableComplicationTypesByExample = [
    ComplicationType.shortText,
    ComplicationType.rangedValue,
    ComplicationType.icon,
    ComplicationType.smallImage,
    ComplicationType.largeImage
  ];

  ComplicationSelectorScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: ComplicationReceiver(
            supportedTypeList: supportedTypeList,
            builder: (context, complications) => SizedBox(
                  width: MediaQuery.of(context).size.width,
                  child: SingleChildScrollView(
                      child: Padding(
                          padding: const EdgeInsets.fromLTRB(12, 36, 12, 36),
                          child: Column(children: [
                            ...complications.mapIndexed((index, complication) =>
                                ComplicationButton(complication: complication, title: "Complication ${index + 1}")),
                          ]))),
                )));
  }
}
