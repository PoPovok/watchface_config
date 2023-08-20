import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:collection/collection.dart';

import 'model/complication.dart';
import 'model/complication_info.dart';
import 'model/complication_type.dart';
import 'model/complication_signing.dart';
import 'model/complication_slot_info.dart';

class ComplicationReceiver extends StatefulWidget {
  final Widget Function(BuildContext, List<Complication>) builder;
  final Widget? loading;
  late final List<List<int>> supportedTypeList;
  final Function(List<Complication>, List<Complication>)? onBeforeChange;
  final Function(List<Complication>, List<Complication>)? onAfterChange;

  ComplicationReceiver({super.key,
    required this.builder,
    required List<List<ComplicationType>> supportedTypeList,
    this.onBeforeChange,
    this.onAfterChange,
    this.loading}) {
    this.supportedTypeList =
        supportedTypeList.map((supportedTypes) => supportedTypes.map((element) => element.value).toList()).toList();
  }

  @override
  State<StatefulWidget> createState() => _ComplicationReceiver();
}

class _ComplicationReceiver extends State<ComplicationReceiver> {
  final MethodChannel methodChannel = const MethodChannel("Config");
  List<Complication> complications = List.empty(growable: true);
  var _isInitialized = false;

  @override
  void initState() {
    init();
    super.initState();
  }

  @override
  void didUpdateWidget(ComplicationReceiver oldWidget) {
    _sendComplicationData();
    super.didUpdateWidget(oldWidget);
  }

  void _sendComplicationData() {
    methodChannel.invokeMethod("retrieveInitialComplicationsData", {"supportedTypeList": widget.supportedTypeList});
  }

  Future<void> init() async {
    methodChannel.setMethodCallHandler(methodChannelReceiver);
    _sendComplicationData();
  }

  Future<dynamic> methodChannelReceiver(MethodCall call) async {
    switch (call.method) {
      case "retrieveInitialComplicationsData":
        {
          _retrieveInitialComplicationsData(call.arguments as List<dynamic>);
          break;
        }
      case "updateComplicationsData":
        {
          _updateComplicationData(call.arguments as Map);
          break;
        }
      default:
        {
          break;
        }
    }
  }

  void _retrieveInitialComplicationsData(List<dynamic> rawComplications) {
    final oldComplications = List<Complication>.from(complications);
    final newComplications = ((rawComplications).mapIndexed((index, element) {
      final complicationElement = Map<String, dynamic>.from(element as Map);
      if (complicationElement["complicationInfo"] != null) {
        complicationElement["complicationInfo"] = Map<String, dynamic>.from(complicationElement["complicationInfo"] as Map);
      }
      final compilationInformation = ComplicationSlotInfo.fromJson(complicationElement);
      return Complication(compilationInformation, () {
        methodChannel.invokeMethod(
            "launchHelperActivity", ComplicationSigning(index, compilationInformation.supportedTypes).toJson());
      });
    }).toList(growable: false));

    _changeComplications(oldComplications, newComplications);
    _isInitialized = true;
  }

  void _updateComplicationData(Map rawComplicationData) {
    final rawComplicationWithIndex = Map<String, dynamic>.from(rawComplicationData);
    final oldComplications = List<Complication>.from(complications);
    final index = rawComplicationWithIndex.remove("index") as int;

    final complicationInformation = rawComplicationWithIndex.isNotEmpty
        ? ComplicationInfo.fromJson(rawComplicationWithIndex)
        : null;
    final newComplications = List<Complication>.from(complications);
    newComplications[index] = Complication(
        ComplicationSlotInfo(complicationInformation, complications[index].complicationSlotInfo.supportedTypes),
        complications[index].launchComplicationActivity
    );

    _changeComplications(oldComplications, newComplications);
  }

  void _changeComplications(List<Complication> oldComplications, List<Complication> newComplications) {
    if (!_isInitialized) {
      setState(() {
        complications = newComplications;
      });
      return;
    }

    widget.onBeforeChange?.call(oldComplications.toList(), newComplications.toList());
    setState(() {
      complications = newComplications;
    });
    widget.onAfterChange?.call(oldComplications.toList(), newComplications.toList());
  }

  @override
  Widget build(BuildContext context) {
    return _isInitialized
        ? widget.builder(context, complications)
        : (widget.loading ?? widget.builder(context, complications));
  }
}
