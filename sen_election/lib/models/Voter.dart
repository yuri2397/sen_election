import 'dart:convert';

import 'package:sen_election/models/Part.dart';
import 'package:sen_election/models/Zone.dart';

Voter voterFromJson(String str) => Voter.fromJson(json.decode(str));

class Voter {
  Voter({
    required this.id,
    required this.firstName,
    required this.lastName,
    required this.dateBorn,
    required this.size,
    required this.cni,
    required this.cne,
    required this.dateExpiration,
    required this.dateDelivery,
    required this.avatar,
    required this.sex,
    required this.authType,
    required this.voteLocation,
    required this.bornAddress,
    required this.medias,
    required this.voterPart,
  });

  int id;
  String firstName;
  String lastName;
  DateTime dateBorn;
  double size;
  int cni;
  int cne;
  DateTime dateExpiration;
  DateTime dateDelivery;
  String avatar;
  String sex;
  String authType;
  Zone voteLocation;
  Zone bornAddress;
  List<dynamic>? medias;
  Part? voterPart;

  factory Voter.fromJson(Map<String, dynamic> json) => Voter(
        id: json["id"],
        firstName: json["firstName"],
        lastName: json["lastName"],
        dateBorn: DateTime.parse(json["dateBorn"]),
        size: json["size"].toDouble(),
        cni: json["cni"],
        cne: json["cne"],
        dateExpiration: DateTime.parse(json["dateExpiration"]),
        dateDelivery: DateTime.parse(json["dateDelivery"]),
        avatar: json["avatar"],
        sex: json["sex"],
        authType: json["authType"],
        voteLocation: Zone.fromJson(json["voteLocation"]),
        bornAddress: Zone.fromJson(json["bornAddress"]),
        medias: json["medias"] == null
            ? null
            : List<dynamic>.from(json["medias"].map((x) => x)),
        voterPart: json["part"] == null ? null : Part.fromJson(json["part"]),
      );
}
