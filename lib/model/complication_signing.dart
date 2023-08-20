
part 'complication_signing.g.dart';

class ComplicationSigning {
  int ordinalNumber;
  List<int> supportedTypes;

  ComplicationSigning(this.ordinalNumber, this.supportedTypes);

  factory ComplicationSigning.fromJson(Map<String, dynamic> json) => _$ComplicationSigningFromJson(json);
  Map<String, dynamic> toJson() => _$ComplicationSigningToJson(this);
}
