import 'dart:developer';

import 'package:flutter/material.dart';
import 'package:mask_text_input_formatter/mask_text_input_formatter.dart';
import 'package:sen_election/config/ThemeConfig.dart';
import 'package:sen_election/models/InitVoteResponse.dart';
import 'package:sen_election/services/VoteService.dart';
import 'package:sen_election/utils/Utils.dart';
import 'package:sen_election/widget/VoterPage.dart';

class HomePage extends StatefulWidget {
  HomePage({Key? key}) : super(key: key);

  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  late int cne;
  var maskFormatter = new MaskTextInputFormatter(
    mask: '#-###-###-###-###',
  );

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.white,
        elevation: 0,
      ),
      body: Center(
        child: Container(
          width: double.infinity,
          color: Colors.white,
          child: Stack(
            children: [
              Container(
                width: double.infinity,
                child: Column(
                  children: [
                    Text(
                      "Bienvenue",
                      style:
                          TextStyle(fontFamily: "Poppins Bold", fontSize: 20),
                      textAlign: TextAlign.justify,
                    ),
                    Text(
                      "Voter avec plus de securité",
                      style:
                          TextStyle(fontFamily: "Poppins Light", fontSize: 15),
                      textAlign: TextAlign.justify,
                    ),
                    SizedBox(
                      height: 10,
                    ),
                    Text(
                      "L'élection présidentielle sénégalaise",
                      style: TextStyle(
                          fontFamily: "Poppins Light",
                          fontSize: 15,
                          color: Colors.grey),
                      textAlign: TextAlign.justify,
                    ),
                  ],
                ),
              ),
              SizedBox(
                height: 30,
              ),
              Center(child: Image.asset('assets/images/home_page_2.jpg')),
              Positioned(
                left: 10,
                right: 10,
                bottom: 10,
                child: InkWell(
                  onTap: () async {
                    await openCneModel();
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
                          offset: Offset(0, 3), // changes position of shadow
                        ),
                      ],
                    ),
                    child: Center(
                      child: Text(
                        "Commencer",
                        style: TextStyle(
                            color: Colors.white,
                            fontFamily: "Poppins Light",
                            fontWeight: FontWeight.bold),
                      ),
                    ),
                  ),
                ),
              )
            ],
          ),
        ),
      ),
    );
  }

  Future openCneModel() async {
    return showModalBottomSheet(
        shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.vertical(top: Radius.circular(25.0))),
        backgroundColor: Colors.white,
        context: context,
        isScrollControlled: true,
        builder: (context) => Padding(
              padding: const EdgeInsets.symmetric(horizontal: 18, vertical: 10),
              child: Column(
                mainAxisSize: MainAxisSize.min,
                children: [
                  SizedBox(
                    height: 10,
                  ),
                  Text(
                    "Numéro electeur",
                    style: TextStyle(fontFamily: "Poppins Light", fontSize: 15),
                    textAlign: TextAlign.justify,
                  ),
                  SizedBox(
                    height: 10,
                  ),
                  Container(
                    padding: EdgeInsets.symmetric(horizontal: 20, vertical: 0),
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
                        String v = trim(value);
                        log(v, name: "CNE");
                        cne = int.parse(v);
                      },
                      cursorColor: ThemeConfig.primaryColor,
                      keyboardType: TextInputType.number,
                      inputFormatters: [maskFormatter],
                      autofocus: false,
                      onSubmitted: (String value) {},
                      decoration: InputDecoration(
                        hintText: "N° carte electeur",
                        border: InputBorder.none,
                      ),
                    ),
                  ),
                  SizedBox(
                    height: 10,
                  ),
                  InkWell(
                    onTap: () async {
                      Utils.showLoaderDialog(context, "Traitement en cours...");
                      VoteService service = VoteService();
                      InitVoteResponse? res;
                      try {
                        res = await service.initVote("init-vote", cne);
                        print("RES, $res");
                        if (res == null) {
                          FocusScope.of(context).requestFocus(FocusNode());
                          Navigator.pop(context);
                          ScaffoldMessenger.of(context).showSnackBar(
                            SnackBar(
                              backgroundColor: Colors.red,
                              behavior: SnackBarBehavior.floating,
                              content: const Text(
                                  'Vous n\'etes pas dans les listes electorales.'),
                            ),
                          );
                        } else {
                          print("AUTH SUCCES");
                          Navigator.pop(context);
                          FocusScope.of(context).requestFocus(FocusNode());
                          Navigator.push(
                              context,
                              MaterialPageRoute(
                                  builder: (BuildContext context) =>
                                      VoterPage(res!)));
                        }
                      } catch (e) {
                        Navigator.pop(context);
                        FocusScope.of(context).requestFocus(FocusNode());
                        print("CATCH : " + e.toString());
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
                            offset: Offset(0, 3), // changes position of shadow
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
            ));
  }

  String trim(String value) {
    String v = "";
    for (int i = 0; i < value.length; i++) {
      if (value[i] != "-") v += value[i];
    }
    return v;
  }
}
