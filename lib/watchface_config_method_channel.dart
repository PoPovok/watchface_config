import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'watchface_config_platform_interface.dart';

/// An implementation of [WatchfaceConfigPlatform] that uses method channels.
class MethodChannelWatchfaceConfig extends WatchfaceConfigPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('watchface_config');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }
}
