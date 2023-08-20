// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'complication_signing.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

ComplicationSigning _$ComplicationSigningFromJson(Map<String, dynamic> json) =>
    ComplicationSigning(
      json['ordinalNumber'] as int,
      (json['supportedTypes'] as List<dynamic>).map((e) => e as int).toList(),
    );

Map<String, dynamic> _$ComplicationSigningToJson(
        ComplicationSigning instance) =>
    <String, dynamic>{
      'ordinalNumber': instance.ordinalNumber,
      'supportedTypes': instance.supportedTypes,
    };
