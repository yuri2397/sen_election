import 'dart:convert';

import 'package:sen_election/models/Election.dart';

import 'Voter.dart';

InitVoteResponse initVoteResponseFromJson(String str) =>
    InitVoteResponse.fromJson(json.decode(str));

class InitVoteResponse {
  InitVoteResponse(
      {required this.candidates, required this.voter, required this.election});

  List<Voter> candidates;
  Voter voter;
  Election election;

  factory InitVoteResponse.fromJson(Map<String, dynamic> json) =>
      InitVoteResponse(
        election: Election.fromJson(json["election"]),
        candidates:
            List<Voter>.from(json["candidates"].map((x) => Voter.fromJson(x))),
        voter: Voter.fromJson(json["voter"]),
      );
}
