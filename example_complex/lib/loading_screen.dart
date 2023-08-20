import 'package:flutter/cupertino.dart';

class LoadingScreen extends StatelessWidget {
  const LoadingScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return const Center(
      child: Padding(padding: EdgeInsets.all(20), child: Text("Loading Complications")),
    );
  }
}
