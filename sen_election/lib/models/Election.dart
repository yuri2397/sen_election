import 'dart:convert';

Election electionFromJson(String str) => Election.fromJson(json.decode(str));

String electionToJson(Election data) => json.encode(data.toJson());

class Election {
  Election({
    required this.id,
    required this.title,
    required this.date,
    required this.status,
    required this.description,
    required this.openAt,
    required this.closeAt,
  });

  int id;
  String title;
  DateTime date;
  String status;
  String description;
  String openAt;
  String closeAt;

  factory Election.fromJson(Map<String, dynamic> json) => Election(
        id: json["id"],
        title: json["title"],
        date: DateTime.parse(json["date"]),
        status: json["status"],
        description: json["description"],
        openAt: json["openAt"],
        closeAt: json["closeAt"],
      );

  Map<String, dynamic> toJson() => {
        "id": id,
        "title": title,
        "date":
            "${date.year.toString().padLeft(4, '0')}-${date.month.toString().padLeft(2, '0')}-${date.day.toString().padLeft(2, '0')}",
        "status": status,
        "description": description,
        "openAt": openAt,
        "closeAt": closeAt,
      };
}
