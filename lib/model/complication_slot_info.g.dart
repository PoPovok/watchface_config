// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'complication_slot_info.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

ComplicationSlotInfo _$ComplicationSlotInfoFromJson(
        Map<String, dynamic> json) =>
    ComplicationSlotInfo(
      json['complicationInfo'] == null
          ? null
          : ComplicationInfo.fromJson(
              json['complicationInfo'] as Map<String, dynamic>),
      (json['supportedTypes'] as List<dynamic>).map((e) => e as int).toList(),
    );

Map<String, dynamic> _$ComplicationSlotInfoToJson(
        ComplicationSlotInfo instance) =>
    <String, dynamic>{
      'complicationInfo': instance.complicationInfo,
      'supportedTypes': instance.supportedTypes,
    };
