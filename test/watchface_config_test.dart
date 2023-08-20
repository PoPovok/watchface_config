import 'package:flutter_test/flutter_test.dart';
import 'package:watchface_config/watchface_config.dart';
import 'package:watchface_config/watchface_config_platform_interface.dart';
import 'package:watchface_config/watchface_config_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockWatchfaceConfigPlatform
    with MockPlatformInterfaceMixin
    implements WatchfaceConfigPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final WatchfaceConfigPlatform initialPlatform = WatchfaceConfigPlatform.instance;

  test('$MethodChannelWatchfaceConfig is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelWatchfaceConfig>());
  });

  test('getPlatformVersion', () async {
    WatchfaceConfig watchfaceConfigPlugin = WatchfaceConfig();
    MockWatchfaceConfigPlatform fakePlatform = MockWatchfaceConfigPlatform();
    WatchfaceConfigPlatform.instance = fakePlatform;

    expect(await watchfaceConfigPlugin.getPlatformVersion(), '42');
  });
}
