import 'dart:convert';
import 'dart:typed_data';

part 'complication_info.g.dart';

class ComplicationInfo {
  String providerName;
  Uint8List? complicationIcon;
  int complicationType;

  ComplicationInfo(this.providerName, String? complicationIcon, this.complicationType) {
    this.complicationIcon = complicationIcon == null ? null : const Base64Decoder()
        .convert(complicationIcon.replaceAll(RegExp(r'\s+'), ''));
    }

  factory ComplicationInfo.fromJson(Map<String, dynamic> json) => _$ComplicationInfoFromJson(json);

  Map<String, dynamic> toJson() => _$ComplicationInfoToJson(this);
}