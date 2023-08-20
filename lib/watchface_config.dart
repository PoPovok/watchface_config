
import 'watchface_config_platform_interface.dart';

class WatchfaceConfig {
  Future<String?> getPlatformVersion() {
    return WatchfaceConfigPlatform.instance.getPlatformVersion();
  }
}
