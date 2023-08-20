import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:watchface_config/watchface_config_method_channel.dart';

void main() {
  MethodChannelWatchfaceConfig platform = MethodChannelWatchfaceConfig();
  const MethodChannel channel = MethodChannel('watchface_config');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await platform.getPlatformVersion(), '42');
  });
}
