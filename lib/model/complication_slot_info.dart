
import 'complication_info.dart';

part 'complication_slot_info.g.dart';

class ComplicationSlotInfo {
  ComplicationInfo? complicationInfo;
  List<int> supportedTypes;

  ComplicationSlotInfo(this.complicationInfo, this.supportedTypes);

  factory ComplicationSlotInfo.fromJson(Map<String, dynamic> json) => _$ComplicationSlotInfoFromJson(json);

  Map<String, dynamic> toJson() => _$ComplicationSlotInfoToJson(this);
}