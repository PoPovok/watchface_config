// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'complication_info.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

ComplicationInfo _$ComplicationInfoFromJson(Map<String, dynamic> json) =>
    ComplicationInfo(
      json['providerName'] as String,
      json['complicationIcon'] as String?,
      json['complicationType'] as int,
    );

Map<String, dynamic> _$ComplicationInfoToJson(ComplicationInfo instance) =>
    <String, dynamic>{
      'providerName': instance.providerName,
      'complicationIcon': instance.complicationIcon,
      'complicationType': instance.complicationType,
    };
