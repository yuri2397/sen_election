import 'dart:convert';
import 'dart:developer';

import 'package:sen_election/config/ApiConfig.dart';
import 'package:sen_election/models/InitVoteResponse.dart';
import 'package:http/http.dart' as http;
import 'package:sen_election/models/Vote.dart';
import 'package:sen_election/models/Voter.dart';

class VoteService {
  final baseUrl = ApiConfig.voteUrl;

  static final Map<String, String> headers = {
    'Content-Type': 'application/json',
    'Accept': 'application/json',
  };

  final uri = Uri.parse(ApiConfig.voteUrl);

  Future<InitVoteResponse?> initVote(String url, int cne) async {
    var _queryParameters = json.encode({
      'cne': cne,
    });
    var uri = Uri.parse(baseUrl + url);
    var client = new http.Client();
    var response =
        await client.post(uri, headers: headers, body: _queryParameters);
    if (response.statusCode == 200) {
      InitVoteResponse data = initVoteResponseFromJson(response.body);
      return data;
    } else if (response.statusCode == 400) {
      return null;
    } else {
      throw Exception(
          {"message": "Vous n'étes pas dans les liste electorale."});
    }
  }

  Future<InitVoteResponse?> confirmation(String url, int cne) async {
    var _queryParameters = json.encode({
      'cne': cne,
    });
    var uri = Uri.parse(baseUrl + url);
    var client = new http.Client();
    var response =
        await client.post(uri, headers: headers, body: _queryParameters);
    if (response.statusCode == 200) {
      InitVoteResponse data = initVoteResponseFromJson(response.body);
      return data;
    } else if (response.statusCode == 400) {
      return null;
    } else {
      throw Exception(
          {"message": "Vous n'étes pas dans les liste electorale."});
    }
  }

  Future<Vote?> doVote(Voter voter, Voter candidate, String finger) async {
    var _queryParameters = json.encode(
        {"voter": voter.id, "candidate": candidate.id, "finger": finger});

    var uri = Uri.parse(baseUrl + "do-vote");
    var client = new http.Client();
    var response =
        await client.post(uri, headers: headers, body: _queryParameters);
    log(response.toString(), name: "RESPONSE");

    if (response.statusCode == 200) {
      return Vote.fromJson(json.decode(response.body));
    } else if (response.statusCode == 401) {
      throw "Authenfication rejeter.";
    } else if (response.statusCode == 422) {
      throw "Vérifier les informations saisies.";
    } else if (response.statusCode == 409) {
      throw "Vous avez déjà voté.";
    }
  }
}
