import 'package:flutter/material.dart';
import 'package:sen_election/config/ApiConfig.dart';
import 'package:sen_election/config/ThemeConfig.dart';
import 'package:sen_election/models/Voter.dart';
import 'package:sen_election/utils/HexColor.dart';
import 'package:sen_election/widget/ConfirmVote.dart';

class CandidateItem extends StatefulWidget {
  final Voter candidate;
  final Voter voter;
  CandidateItem(this.candidate, this.voter, {Key? key}) : super(key: key);

  @override
  _CandidateItemState createState() => _CandidateItemState();
}

class _CandidateItemState extends State<CandidateItem> {
  @override
  Widget build(BuildContext context) {
    return Container(
      margin: EdgeInsets.symmetric(horizontal: 10, vertical: 10),
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.only(
            topLeft: Radius.circular(10),
            topRight: Radius.circular(10),
            bottomLeft: Radius.circular(10),
            bottomRight: Radius.circular(10)),
        boxShadow: [
          BoxShadow(
            color: Colors.grey.withOpacity(0.3),
            spreadRadius: 2,
            blurRadius: 5,
            offset: Offset(0, 1), // changes position of shadow
          ),
        ],
      ),
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
              color:
                  HexColor.fromHex(widget.candidate.voterPart!.colorSecondary),
              child: TextButton(
                onPressed: _onItemTap,
                child: Text("VOTER POUR",
                    style: TextStyle(
                        color: Colors.white, fontFamily: "Poppins Bold")),
              ))
        ],
      ),
    );
  }

  _onItemTap() async {
    await Navigator.push(
        context,
        MaterialPageRoute(
            builder: (_) => ConfirmVote(widget.candidate, widget.voter)));
  }
}
