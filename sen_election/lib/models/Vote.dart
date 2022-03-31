import 'dart:convert';

List<Vote> votesFromJson(String str) =>
    List<Vote>.from(json.decode(str).map((x) => Vote.fromJson(x)));

class Vote {
  Vote({
    required this.id,
    required this.mask,
    required this.time,
  });

  int id;
  String mask;
  DateTime time;

  factory Vote.fromJson(Map<String, dynamic> json) => Vote(
        id: json["id"],
        mask: json["mask"],
        time: DateTime.parse(json["time"]),
      );

  Map<String, dynamic> toJson() => {
        "id": id,
        "mask": mask,
        "time": time.toIso8601String(),
      };
}
