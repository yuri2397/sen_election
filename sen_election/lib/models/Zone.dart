import 'dart:convert';

Zone zoneFromJson(String str) => Zone.fromJson(json.decode(str));

String zoneToJson(Zone data) => json.encode(data.toJson());

class Zone {
  Zone({
    required this.id,
    required this.latitude,
    required this.longitude,
    required this.name,
  });

  int id;
  double latitude;
  double longitude;
  String name;

  factory Zone.fromJson(Map<String, dynamic> json) => Zone(
        id: json["id"],
        latitude: json["latitude"].toDouble(),
        longitude: json["longitude"].toDouble(),
        name: json["name"],
      );

  Map<String, dynamic> toJson() => {
        "id": id,
        "latitude": latitude,
        "longitude": longitude,
        "name": name,
      };
}
