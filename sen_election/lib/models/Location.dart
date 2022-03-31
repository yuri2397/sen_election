import 'Voter.dart';

class Location {
  Location({
    required this.id,
    required this.latitude,
    required this.longitude,
    required this.name,
    required this.voters,
  });

  int id;
  double latitude;
  double longitude;
  String name;
  List<Voter> voters;

  factory Location.fromJson(Map<String, dynamic> json) => Location(
        id: json["id"],
        latitude: json["latitude"].toDouble(),
        longitude: json["longitude"].toDouble(),
        name: json["name"],
        voters: List<Voter>.from(json["voters"].map((x) => Voter.fromJson(x))),
      );
}
