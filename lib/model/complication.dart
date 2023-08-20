
import 'complication_slot_info.dart';

class Complication{
  final ComplicationSlotInfo complicationSlotInfo;
  final Function() launchComplicationActivity;

  const Complication(this.complicationSlotInfo, this.launchComplicationActivity);
}