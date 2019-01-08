/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, { Component } from 'react';
import { Platform, StyleSheet, Text, View, TextInput, Button } from 'react-native';

const instructions = Platform.select({
  ios: 'Press Cmd+R to reload,\n' + 'Cmd+D or shake for dev menu',
  android:
    'Double tap R on your keyboard to reload,\n' +
    'Shake or press menu button for dev menu',
});







export default class App extends Component {
  state = {
    placeName: '',
    places: []
  }

  placeNameChangeHandler = val => {
    this.setState({
      placeName: val, // set placeName equal val
    });
  }

  placeSubmitHandler = () => {
    if (this.state.placeName.trim() === ""){
      return;
    }

    this.setState(preStave => {
      return{
        places: preStave.places.concat(preStave.placeName)
      };
    })
  };


  render() {
    const placesOutput = this.state.places.map(
      (place,i) => (<Text key={i}>{place}</Text>)
    )
    return (
      <View style={styles.container}>
        <TextInput
          placeholder="Input your name !"
          value={this.state.placeName}
          onChangeText={this.placeNameChangeHandler}
          style={styles.inputTag}
        />
        <View style = {styles.buttonContainer}>
        <Button 
          title = 'Add'
          style = {{marginRight: 30}}
          onPress = {this.placeSubmitHandler}
        />
        </View>
        <View>{placesOutput}</View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    //flex: 1,
    //justifyContent: 'space-between',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },

  inputTag: {
    width: 300, 
    borderColor: 'lightgray', 
    borderWidth: 1
  },
  buttonContainer: {
    //flex:1,
    flexDirection: "row",
    justifyContent: 'space-between',
    

  }
});
