import 'package:collection/collection.dart';
import 'package:flutter/material.dart';
import 'package:watchface_config/complication_receiver.dart';
import 'package:watchface_config/model/complication_type.dart';
import 'package:watchface_config/watchface_info_provider.dart';

import 'buttons.dart';

class ComplicationSelectorScreenFirst extends StatelessWidget {
  final Map<String, List<ComplicationType>> supportedTypeList = {
    "Left": acceptableComplicationTypesByExample.toList(),
    "Top": acceptableComplicationTypesByExample.toList(),
    "Bottom": acceptableComplicationTypesByExample.toList()
  };

  static const acceptableComplicationTypesByExample = [
    ComplicationType.shortText,
    ComplicationType.rangedValue,
    ComplicationType.icon,
    ComplicationType.smallImage,
    ComplicationType.largeImage
  ];

  ComplicationSelectorScreenFirst({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: ComplicationReceiver(
            supportedTypeList: supportedTypeList.values.toList(),
            builder: (context, complications) => SizedBox(
                width: MediaQuery.of(context).size.width,
                child: SingleChildScrollView(
                    child: Padding(
                        padding: const EdgeInsets.fromLTRB(12, 36, 12, 36),
                        child: WatchFaceInfoProvider(builder: (context, watchfaceInfo) {
                          return Column(children: [
                            Text(
                              watchfaceInfo.name,
                              style: const TextStyle(fontSize: 10),
                              textAlign: TextAlign.center,
                            ),
                            ...complications.mapIndexed((index, complication) => ComplicationButton(
                                complication: complication, title: supportedTypeList.keys.toList()[index])),
                          ]);
                        }))))));
  }
}
