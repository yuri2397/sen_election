import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:sen_election/config/ApiConfig.dart';
import 'package:sen_election/config/ThemeConfig.dart';
import 'package:sen_election/models/InitVoteResponse.dart';
import 'package:sen_election/widget/DoVotePage.dart';

class VoterPage extends StatefulWidget {
  final InitVoteResponse data;
  VoterPage(this.data, {Key? key}) : super(key: key);

  @override
  _VoterPageState createState() => _VoterPageState();
}

class _VoterPageState extends State<VoterPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      appBar: AppBar(
        centerTitle: true,
        title: Text(widget.data.election.title.toUpperCase(),
            style: TextStyle(
                color: ThemeConfig.primaryColor, fontFamily: "Poppins Medium")),
        backgroundColor: Colors.white,
        elevation: 0,
      ),
      body: SingleChildScrollView(
        child: Column(
          children: [
            Container(
              height: MediaQuery.of(context).size.height * .4,
              width: MediaQuery.of(context).size.width,
              color: Colors.white,
              child: Stack(
                children: [
                  Image.asset(
                    "assets/images/home_page_2.jpg",
                    fit: BoxFit.fill,
                  ),
                  Positioned(
                    bottom: 0,
                    left: 20,
                    right: 20,
                    child: Container(
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
                            spreadRadius: 5,
                            blurRadius: 7,
                            offset: Offset(0, 3), // changes position of shadow
                          ),
                        ],
                      ),
                      height: 80,
                      margin: EdgeInsets.symmetric(horizontal: 20),
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.spaceAround,
                        children: [
                          CircleAvatar(
                              backgroundColor: ThemeConfig.primaryColor,
                              child: Icon(Icons.person, color: Colors.white)),
                          CircleAvatar(
                              backgroundColor: Color(0XFFe9c46a),
                              child: InkWell(
                                onTap: () async {
                                  Navigator.push(
                                      context,
                                      MaterialPageRoute(
                                          builder: (context) =>
                                              DoVotePage(widget.data)));
                                },
                                child: Icon(Icons.move_to_inbox,
                                    color: Colors.white),
                              )),
                          CircleAvatar(
                              backgroundColor: Color(0XFFbb3e03),
                              child: Icon(Icons.insert_chart_outlined,
                                  color: Colors.white))
                        ],
                      ),
                    ),
                  )
                ],
              ),
            ),
            SizedBox(
              height: 20,
            ),
            Container(
              margin: EdgeInsets.symmetric(horizontal: 20),
              padding: EdgeInsets.all(8),
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
                    spreadRadius: 5,
                    blurRadius: 7,
                    offset: Offset(0, 3), // changes position of shadow
                  ),
                ],
              ),
              child: Column(
                mainAxisAlignment: MainAxisAlignment.start,
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Row(
                    children: [
                      Material(
                        borderRadius: BorderRadius.circular(50),
                        color: Color(0XFFe9ecef),
                        elevation: 5,
                        child: ClipRRect(
                          borderRadius: BorderRadius.circular(50),
                          child: Image.network(
                            ApiConfig.resourceUrl + widget.data.voter.avatar,
                            height: 70,
                            width: 70,
                            fit: BoxFit.cover,
                          ),
                        ),
                      ),
                      SizedBox(
                        width: 20,
                      ),
                      Text(
                        "${widget.data.voter.firstName} ${widget.data.voter.lastName}",
                        style: TextStyle(
                            color: Colors.black,
                            fontFamily: "Poppins Light",
                            fontWeight: FontWeight.bold,
                            fontSize: 20),
                      ),
                      SizedBox(width: 10),
                      CircleAvatar(
                        backgroundColor: ThemeConfig.primaryColor,
                        child: Icon(
                            widget.data.voter.sex == "MAN"
                                ? Icons.male
                                : Icons.female,
                            color: Colors.white),
                      ),
                    ],
                  ),
                  SizedBox(
                    height: 20,
                  ),
                  //Date de naissance
                  Row(
                    children: [
                      Icon(Icons.today, color: ThemeConfig.primaryColor),
                      SizedBox(
                        width: 10,
                      ),
                      Text(
                        DateFormat("yyyy / MM / dd")
                            .format(widget.data.voter.dateBorn),
                        style: TextStyle(
                            color: Colors.black,
                            fontFamily: "Poppins Light",
                            fontSize: 13),
                        textAlign: TextAlign.start,
                      )
                    ],
                  ),
                  // Lieu de naissance
                  SizedBox(
                    height: 10,
                  ),
                  Row(
                    children: [
                      Icon(Icons.room, color: ThemeConfig.primaryColor),
                      SizedBox(
                        width: 10,
                      ),
                      Text(
                        widget.data.voter.bornAddress.name,
                        style: TextStyle(
                            color: Colors.black,
                            fontFamily: "Poppins Light",
                            fontSize: 13),
                        textAlign: TextAlign.start,
                      )
                    ],
                  ),
                  SizedBox(
                    height: 10,
                  ),
                  Row(
                    children: [
                      Icon(Icons.inventory_2, color: ThemeConfig.primaryColor),
                      SizedBox(
                        width: 10,
                      ),
                      Text(
                        widget.data.voter.voteLocation.name,
                        style: TextStyle(
                            color: Colors.black,
                            fontFamily: "Poppins Light",
                            fontSize: 13),
                        textAlign: TextAlign.start,
                      )
                    ],
                  ),
                  SizedBox(
                    height: 10,
                  ),

                  Row(
                    children: [
                      Icon(Icons.date_range, color: ThemeConfig.primaryColor),
                      SizedBox(
                        width: 10,
                      ),
                      Text(
                        DateFormat("yyyy / MM / dd")
                                .format(widget.data.voter.dateDelivery) +
                            " à " +
                            DateFormat("yyyy / MM / dd")
                                .format(widget.data.voter.dateDelivery),
                        style: TextStyle(
                            color: Colors.black,
                            fontFamily: "Poppins Light",
                            fontSize: 13),
                        textAlign: TextAlign.start,
                      )
                    ],
                  ),
                  SizedBox(height: 10),
                  Row(
                    children: [
                      Icon(Icons.expand, color: ThemeConfig.primaryColor),
                      SizedBox(
                        width: 10,
                      ),
                      Text(
                        widget.data.voter.size.toString() + " CM",
                        style: TextStyle(
                            color: Colors.black,
                            fontFamily: "Poppins Light",
                            fontSize: 13),
                        textAlign: TextAlign.start,
                      )
                    ],
                  ),
                  SizedBox(
                    height: 20,
                  ),
                  Container(
                    padding: EdgeInsets.all(5.0),
                    width: double.infinity,
                    decoration: BoxDecoration(
                      color: ThemeConfig.primaryColor,
                      borderRadius: BorderRadius.only(
                          topLeft: Radius.circular(10),
                          topRight: Radius.circular(10),
                          bottomLeft: Radius.circular(10),
                          bottomRight: Radius.circular(10)),
                    ),
                    child: Text(
                      "N° CNI " + widget.data.voter.cni.toString(),
                      style: TextStyle(
                          color: Colors.white,
                          fontFamily: "Poppins Medium",
                          fontSize: 13),
                      textAlign: TextAlign.center,
                    ),
                  ),
                ],
              ),
            )
          ],
        ),
      ),
    );
  }
}
