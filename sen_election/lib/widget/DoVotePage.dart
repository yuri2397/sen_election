import 'package:flutter/material.dart';
import 'package:sen_election/config/ThemeConfig.dart';
import 'package:sen_election/models/InitVoteResponse.dart';
import 'package:sen_election/widget/CandidateItem.dart';

class DoVotePage extends StatefulWidget {
  final InitVoteResponse data;
  DoVotePage(this.data, {Key? key}) : super(key: key);

  @override
  _DoVotePageState createState() => _DoVotePageState();
}

class _DoVotePageState extends State<DoVotePage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        backgroundColor: Colors.white,
        appBar: AppBar(
          centerTitle: true,
          title: Text("Liste des candidates",
              style: TextStyle(color: ThemeConfig.primaryColor)),
          backgroundColor: Colors.white,
          elevation: 0,
          leading: Icon(
            Icons.arrow_back_ios_new_rounded,
            color: ThemeConfig.primaryColor,
          ),
        ),
        body: ListView.builder(
            itemCount: widget.data.candidates.length,
            itemBuilder: (context, id) =>
                CandidateItem(widget.data.candidates[id], widget.data.voter)));
  }
}
