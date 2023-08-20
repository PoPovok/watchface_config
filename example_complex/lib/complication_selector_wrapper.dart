import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:watchface_config/model/complication_type.dart';

import 'complication_selector.dart';
import 'loading_screen.dart';

class ComplicationSelectorScreenWrapper extends StatefulWidget {
  final String uniqueWatchfaceId;

  static const acceptableComplicationTypesByExample = [
    ComplicationType.shortText,
    ComplicationType.rangedValue,
    ComplicationType.icon,
    ComplicationType.smallImage,
    ComplicationType.largeImage
  ];

  const ComplicationSelectorScreenWrapper({required this.uniqueWatchfaceId, super.key});

  @override
  State<ComplicationSelectorScreenWrapper> createState() => _ComplicationSelectorScreenWrapper();
}

class _ComplicationSelectorScreenWrapper extends State<ComplicationSelectorScreenWrapper> {
  late Future<List<List<ComplicationType>>> supportedTypeList;

  //it is not required to make it unique if there is only one watchface
  late final String supportedTypeCountForWatchface = "SupportedTypeCount_${widget.uniqueWatchfaceId}";

  @override
  void initState() {
    super.initState();
    supportedTypeList = getSupportedTypes();
  }

  Future<List<List<ComplicationType>>> getSupportedTypes() async {
    final supportedTypeCount = (await SharedPreferences.getInstance()).getInt(supportedTypeCountForWatchface);
    return List.generate(
        supportedTypeCount ?? 3, (_) => ComplicationSelectorScreenWrapper.acceptableComplicationTypesByExample.toList());
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: FutureBuilder(
            future: supportedTypeList,
            builder: (context, snapshot) {
              if (snapshot.data == null) {
                return const LoadingScreen();
              } else {
                return ComplicationSelectorScreen(
                    supportedTypeList: snapshot.data!, supportedTypeCountForWatchface: supportedTypeCountForWatchface);
              }
            }));
  }
}
