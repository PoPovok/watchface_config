
part 'watchface_info.g.dart';

class WatchFaceInfo {
  final String packageName;
  final String className;
  final String fullName;
  final String name;

  WatchFaceInfo(this.packageName, this.className, this.fullName, this.name);

  factory WatchFaceInfo.fromJson(Map<String, dynamic> json) => _$WatchFaceInfoFromJson(json);

  Map<String, dynamic> toJson() => _$WatchFaceInfoToJson(this);
}
