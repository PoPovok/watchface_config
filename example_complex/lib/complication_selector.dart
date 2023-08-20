import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:watchface_config/complication_receiver.dart';
import 'package:watchface_config/model/complication.dart';
import 'package:collection/collection.dart';
import 'package:watchface_config/model/complication_type.dart';

import 'buttons.dart';
import 'complication_selector_wrapper.dart';
import 'loading_screen.dart';

class ComplicationSelectorScreen extends StatefulWidget {
  final List<List<ComplicationType>> supportedTypeList;  //it is not required to make it unique if there is only one watchface
  final String supportedTypeCountForWatchface;

  const ComplicationSelectorScreen({required this.supportedTypeList, required this.supportedTypeCountForWatchface, super.key});

  @override
  State<StatefulWidget> createState() => _ComplicationSelectorScreen();
}

class _ComplicationSelectorScreen extends State<ComplicationSelectorScreen> {
  late var supportedTypeList = widget.supportedTypeList;

  static const maxComplicationsToDisplay = 4;

  void onBeforeChange(List<Complication> complicationsBefore, List<Complication> complicationsAfter) {
    print("Change is about to happen");
  }

  void onAfterChange(List<Complication> complicationsBefore, List<Complication> complicationsAfter) async {
    (await SharedPreferences.getInstance()).setInt(widget.supportedTypeCountForWatchface, complicationsAfter.length);
  }

  void addComplication() {
    setState(() {
      supportedTypeList = [...supportedTypeList, ComplicationSelectorScreenWrapper.acceptableComplicationTypesByExample];
    });
  }

  void removeLastComplication() {
    if (supportedTypeList.isNotEmpty) {
      setState(() {
        supportedTypeList = supportedTypeList.sublist(0, supportedTypeList.length - 1);
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return ComplicationReceiver(
        supportedTypeList: supportedTypeList,
        onBeforeChange: onBeforeChange,
        onAfterChange: onAfterChange,
        loading: const LoadingScreen(),
        builder: (context, complications) => SizedBox(
              width: MediaQuery.of(context).size.width,
              child: SingleChildScrollView(
                  child: Padding(
                      padding: const EdgeInsets.fromLTRB(12, 36, 12, 36),
                      child: Column(children: [
                        ...complications.mapIndexed((index, complication) =>
                            ComplicationButton(complication: complication, title: "Complication ${index + 1}")),
                        complications.length < maxComplicationsToDisplay
                            ? CustomButton(
                                onPressed: addComplication,
                                title: "Add Complication",
                                image: const Icon(Icons.add, color: Colors.white),
                                theme: Colors.green,
                              )
                            : Container(),
                        complications.isNotEmpty
                            ? CustomButton(
                                onPressed: removeLastComplication,
                                title: "Remove Complication",
                                image: const Icon(Icons.close, color: Colors.white),
                                theme: Colors.red,
                              )
                            : Container()
                      ]))),
            ));
  }
}
