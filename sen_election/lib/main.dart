import 'package:flutter/material.dart';
import 'package:intl/date_symbol_data_local.dart';
import 'package:sen_election/widget/HomePage.dart';

void main() {
  initializeDateFormatting('fr_FR', null).then((_) => runApp(MyApp()));
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: HomePage(),
    );
  }
}
