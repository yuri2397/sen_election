import 'package:flutter/material.dart';
import 'package:sen_election/config/ThemeConfig.dart';
import 'package:sleek_circular_slider/sleek_circular_slider.dart';

class Utils {
  static Future showLoaderDialog(BuildContext context, String title,
      {closable = false}) async {
    AlertDialog alert = AlertDialog(
      content: new Row(
        children: [
          progressBar(),
          Container(margin: EdgeInsets.only(left: 7), child: Text(title)),
        ],
      ),
    );
    return showDialog(
      barrierDismissible: closable,
      context: context,
      builder: (BuildContext context) {
        return alert;
      },
    );
  }

  static Widget progressBar() {
    return Container(
      height: 50,
      width: 50,
      alignment: Alignment.center,
      child: SleekCircularSlider(
        initialValue: 10,
        max: 80,
        appearance: CircularSliderAppearance(
            angleRange: 360,
            spinnerMode: true,
            startAngle: 90,
            size: 40,
            customColors: CustomSliderColors(
              hideShadow: true,
              progressBarColor: ThemeConfig.primaryColor,
            )),
      ),
    );
  }
}
