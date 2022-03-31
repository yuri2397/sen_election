import 'dart:developer';

import 'package:flutter/material.dart';
import 'package:sen_election/config/ApiConfig.dart';
import 'package:sen_election/config/ThemeConfig.dart';
import 'package:sen_election/models/Vote.dart';
import 'package:sen_election/models/Voter.dart';
import 'package:sen_election/services/VoteService.dart';
import 'package:sen_election/utils/HexColor.dart';
import 'package:sen_election/utils/Utils.dart';

class ConfirmVote extends StatefulWidget {
  final Voter candidate;
  final Voter voter;
  ConfirmVote(this.candidate, this.voter, {Key? key}) : super(key: key);

  @override
  _ConfirmVoteState createState() => _ConfirmVoteState();
}

class _ConfirmVoteState extends State<ConfirmVote> {
  String finger = "";

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        child: Column(
          children: [
            ClipRRect(
              borderRadius: BorderRadius.only(
                  topLeft: Radius.circular(10), topRight: Radius.circular(10)),
              child: Image.network(
                ApiConfig.resourceUrl + widget.candidate.avatar,
                fit: BoxFit.cover,
              ),
            ),
            SizedBox(
              height: 30,
            ),
            Container(
              width: MediaQuery.of(context).size.width,
              child: Text(
                "Pr. ${widget.candidate.firstName} ${widget.candidate.lastName}",
                style: TextStyle(fontFamily: "Poppins Bold", fontSize: 20),
                textAlign: TextAlign.center,
              ),
            ),
            Divider(),
            //Date de naissance
            Container(
              height: 50,
              padding: const EdgeInsets.all(10.0),
              child: Row(
                children: [
                  Icon(Icons.group_work, color: ThemeConfig.primaryColor),
                  SizedBox(
                    width: 10,
                  ),
                  Text(
                    widget.candidate.voterPart!.name,
                    style: TextStyle(
                        color: Colors.black,
                        fontFamily: "Poppins Medium",
                        fontSize: 15),
                    textAlign: TextAlign.start,
                  )
                ],
              ),
            ),
            // Lieu de naissance
            SizedBox(
              height: 3,
            ),
            Container(
              height: 50,
              padding: const EdgeInsets.all(10.0),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.start,
                children: [
                  Icon(Icons.campaign, color: ThemeConfig.primaryColor),
                  SizedBox(
                    width: 10,
                  ),
                  Text(
                    widget.candidate.voterPart!.slogan,
                    style: TextStyle(
                        color: Colors.black,
                        fontFamily: "Poppins Medium",
                        fontSize: 15),
                    textAlign: TextAlign.start,
                  )
                ],
              ),
            ),
            Container(
              height: 50,
              width: MediaQuery.of(context).size.width,
              color: HexColor.fromHex(widget.candidate.voterPart!.colorPrimary),
            ),
            Container(
                padding: EdgeInsets.symmetric(vertical: 10),
                width: MediaQuery.of(context).size.width,
                color: HexColor.fromHex(
                    widget.candidate.voterPart!.colorSecondary),
                child: TextButton(
                  onPressed: _onVoteConf,
                  child: Text("CONFIRMER VOTRE CHOIX",
                      style: TextStyle(
                          color: Colors.white, fontFamily: "Poppins Bold")),
                ))
          ],
        ),
      ),
    );
  }

  _onVoteConf() async {
    await openCneModel();
  }

  Future openCneModel() async {
    return showModalBottomSheet(
        shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.vertical(top: Radius.circular(25.0))),
        backgroundColor: Colors.white,
        context: context,
        isScrollControlled: true,
        builder: (context) => Expanded(
              child: Padding(
                padding:
                    const EdgeInsets.symmetric(horizontal: 18, vertical: 10),
                child: Column(
                  mainAxisSize: MainAxisSize.min,
                  children: [
                    SizedBox(
                      height: 10,
                    ),
                    CircleAvatar(
                      child: Icon(Icons.fingerprint),
                    ),
                    SizedBox(
                      height: 10,
                    ),
                    Container(
                      padding:
                          EdgeInsets.symmetric(horizontal: 20, vertical: 0),
                      width: MediaQuery.of(context).size.width * 0.8,
                      decoration: BoxDecoration(
                        boxShadow: [
                          BoxShadow(
                            color: Colors.grey,
                            offset: Offset(0.0, 0.5), //(x,y)
                            blurRadius: 2.0,
                          ),
                        ],
                        color: Colors.white,
                        borderRadius: BorderRadius.circular(29),
                      ),
                      child: TextField(
                        onChanged: (value) {
                          finger = value;
                        },
                        cursorColor: ThemeConfig.primaryColor,
                        keyboardType: TextInputType.text,
                        autofocus: false,
                        onSubmitted: (String value) {
                          finger = value;
                        },
                        decoration: InputDecoration(
                          hintText: "Empreint digital",
                          border: InputBorder.none,
                        ),
                      ),
                    ),
                    SizedBox(
                      height: 10,
                    ),
                    InkWell(
                      onTap: () async {
                        log("FINGER : $finger, VOTER_NAME : ${widget.voter.firstName} ${widget.voter.lastName}, CANDIDATE: ${widget.candidate.firstName} ${widget.candidate.lastName}");
                        Utils.showLoaderDialog(
                            context, "Traitement en cours...");
                        VoteService service = VoteService();

                        Vote? vote;
                        try {
                          vote = await service.doVote(
                              widget.voter, widget.candidate, finger);

                          if (vote == null) {
                            log("VOTE EST NULL");
                          } else {
                            log("VOTE EST SUCCES");

                            await _voteSuccess();
                          }
                        } catch (e) {
                          FocusScope.of(context).requestFocus(FocusNode());
                          Navigator.pop(context);
                          Navigator.pop(context);
                          ScaffoldMessenger.of(context).showSnackBar(
                            SnackBar(
                              backgroundColor: Colors.red,
                              behavior: SnackBarBehavior.floating,
                              content: Text(e.toString()),
                            ),
                          );
                        }
                      },
                      child: Container(
                        width: double.infinity,
                        height: 50,
                        margin: EdgeInsets.all(20),
                        decoration: BoxDecoration(
                          color: ThemeConfig.primaryColor,
                          borderRadius: BorderRadius.only(
                              topLeft: Radius.circular(10),
                              topRight: Radius.circular(10),
                              bottomLeft: Radius.circular(10),
                              bottomRight: Radius.circular(10)),
                          boxShadow: [
                            BoxShadow(
                              color: Colors.grey.withOpacity(0.3),
                              spreadRadius: 5,
                              blurRadius: 7,
                              offset:
                                  Offset(0, 3), // changes position of shadow
                            ),
                          ],
                        ),
                        child: Center(
                          child: Text(
                            "Continuer",
                            style: TextStyle(
                                color: Colors.white,
                                fontFamily: "Poppins Light",
                                fontWeight: FontWeight.bold),
                          ),
                        ),
                      ),
                    ),
                    Padding(
                        padding: EdgeInsets.only(
                            bottom: MediaQuery.of(context).viewInsets.bottom))
                  ],
                ),
              ),
            ));
  }

  _voteSuccess() async {
    return showDialog<String>(
      context: context,
      builder: (BuildContext context) => AlertDialog(
        title: const Text('Votre s\'est efféctué avec succès.'),
        content: const Text(
            "Être citoyen, c'est se montrer responsable, aussi bien dans sa vie privée que dans sa vie publique."),
        actions: <Widget>[
          TextButton(
            onPressed: () => Navigator.pop(context),
            child: const Text('Terminer'),
          ),
        ],
      ),
    );
  }
}
