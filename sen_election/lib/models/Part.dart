import 'dart:convert';

Part partFromJson(String str) => Part.fromJson(json.decode(str));

String partToJson(Part data) => json.encode(data.toJson());

class Part {
  Part({
    required this.id,
    required this.name,
    required this.slogan,
    required this.colorPrimary,
    required this.colorSecondary,
  });

  int id;
  String name;
  String slogan;
  String colorPrimary;
  String colorSecondary;

  factory Part.fromJson(Map<String, dynamic> json) => Part(
        id: json["id"],
        name: json["name"],
        slogan: json["slogan"],
        colorPrimary: json["colorPrimary"],
        colorSecondary: json["colorSecondary"],
      );

  Map<String, dynamic> toJson() => {
        "id": id,
        "name": name,
        "slogan": slogan,
        "colorPrimary": colorPrimary,
        "colorSecondary": colorSecondary,
      };
}
