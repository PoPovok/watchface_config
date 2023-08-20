import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'watchface_config_method_channel.dart';

abstract class WatchfaceConfigPlatform extends PlatformInterface {
  /// Constructs a WatchfaceConfigPlatform.
  WatchfaceConfigPlatform() : super(token: _token);

  static final Object _token = Object();

  static WatchfaceConfigPlatform _instance = MethodChannelWatchfaceConfig();

  /// The default instance of [WatchfaceConfigPlatform] to use.
  ///
  /// Defaults to [MethodChannelWatchfaceConfig].
  static WatchfaceConfigPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [WatchfaceConfigPlatform] when
  /// they register themselves.
  static set instance(WatchfaceConfigPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
