import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:watchface_config/model/watchface_info.dart';

class WatchFaceInfoProvider extends StatefulWidget {
  final Widget Function(BuildContext, WatchFaceInfo) builder;

  const WatchFaceInfoProvider({required this.builder, super.key});

  @override
  State<StatefulWidget> createState() {
    return _WatchFaceInfoProvider();
  }
}

class _WatchFaceInfoProvider extends State<WatchFaceInfoProvider> {
  final MethodChannel methodChannel = const MethodChannel("WatchFaceInfo");
  late final Future<WatchFaceInfo> watchfaceInfo;

  Future<WatchFaceInfo> retrieveWatchFaceInfo() async {
    return (await methodChannel.invokeMethod<Map>("retrieveWatchFaceInfo").then((value) {
      return WatchFaceInfo.fromJson(Map<String, dynamic>.from(value as Map));
    }));
  }

  @override
  void initState() {
    watchfaceInfo = retrieveWatchFaceInfo();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder<WatchFaceInfo?>(
        future: watchfaceInfo,
        builder: (context, snapshot) {
          if (snapshot.hasData) {
            return widget.builder(context, snapshot.data!);
          } else {
            return Container();
          }
        });
  }
}
