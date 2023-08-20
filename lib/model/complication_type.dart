enum ComplicationType {
  notConfigured(1),
  empty(2),
  noData(10),

  shortText(3),
  longText(4),
  rangedValue(5),
  icon(6),
  smallImage(7),
  largeImage(8);

  const ComplicationType(this.value);

  final int value;
}