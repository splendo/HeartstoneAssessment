//
//  CheckBoxButton.swift
//  SoKi
//
//  Created by Kyle Somers on 11/1/15.
//  Copyright © 2015 Kyle Somers. All rights reserved.
//

import UIKit

class CheckBoxButton: UIButton {

    

    let nameLabel = UILabel()
    let checkBox = UIImageView()
    var checkedImage = UIImage(named: "CheckedBox")
    var uncheckedImage = UIImage(named: "UncheckedBox")
    var isOn = false
    var setupDone = false
    let fontSize : CGFloat = 18
    let smallMargin : CGFloat = 8
    var buttonColor = UIColor()
    var buttonFont = UIFont()
    
    /**
    This will set up the box and set its name
    
    - parameter boxName: the name associated with the checkbox
    */
    func setName(_ boxName : String){
        
        if setupDone == false{
            self.addSubview(nameLabel)
            self.addSubview(checkBox)
            self.addTarget(self, action: #selector(CheckBoxButton.toggle(_:)), for: .touchUpInside)
            setupDone = true
        }
        
        nameLabel.text = boxName
        nameLabel.font = buttonFont
        nameLabel.numberOfLines = 1
        nameLabel.sizeToFit()
        
        checkBox.image = uncheckedImage
        checkBox.frame = CGRect(x: 0, y: 0, width: nameLabel.frame.height, height: nameLabel.frame.height)
        
        nameLabel.frame.origin = CGPoint(x: checkBox.frame.width + smallMargin, y: 0)
        self.frame.size = CGSize(width: checkBox.frame.width + smallMargin + nameLabel.frame.width, height: nameLabel.frame.height)
        
        
        
    }
    
    
    /**
    toggles the button on and off
    
    - parameter sender: always self
    */
    func toggle(_ sender: AnyObject){
        if isOn == true{
            turnOff()
        }
        else{
            turnOn()
        }
        
    }
    /**
    Checks a box
    */
    func turnOn(){
        isOn = true
        nameLabel.textColor = buttonColor
        //nameLabel.font = UIFont(name: "AvenirNext-DemiBold", size: fontSize)
        nameLabel.sizeToFit()
        
        checkBox.image = checkedImage
        checkBox.image = checkBox.image!.withRenderingMode(.alwaysTemplate)
        checkBox.tintColor = buttonColor
    }
    /**
    unchecks a box
    */
    func turnOff(){
        isOn = false
        nameLabel.textColor = UIColor.black
        //nameLabel.font = UIFont(name: "Avenir Next", size: fontSize)
        nameLabel.sizeToFit()
        checkBox.image = uncheckedImage
        checkBox.tintColor = UIColor.black

    }
    /**
    Sets the color
    
    - parameter theColor: The color that you want to set that button to
    */
    func setColor(_ theColor : UIColor){
        buttonColor = theColor
        if isOn{
            nameLabel.textColor = buttonColor
            checkBox.image = checkedImage
            checkBox.image = checkBox.image!.withRenderingMode(.alwaysTemplate)
            checkBox.tintColor = buttonColor
        }
    }
    
    func setShape(_ shape : String){
        if shape.lowercased() == "circle" || shape.lowercased() == "round"{
            checkedImage = UIImage(named: "CheckedCircle")
            uncheckedImage = UIImage(named: "UncheckedCircle")
        }
        else{
            checkedImage = UIImage(named: "CheckedBox")
            uncheckedImage = UIImage(named: "UncheckedBox")
        }
        if isOn{
            checkBox.image = checkedImage
            checkBox.image = checkBox.image!.withRenderingMode(.alwaysTemplate)
            checkBox.tintColor = buttonColor
        }
        else{
            checkBox.image = uncheckedImage

        }
    }
    

}
