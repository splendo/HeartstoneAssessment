//
//  ChecklistView.swift
//  SoKi
//
//  Created by Kyle Somers on 11/1/15.
//  Copyright © 2015 Kyle Somers. All rights reserved.
//

import UIKit

open class ChecklistView: UIView {

    var height : CGFloat = 0
    let boxMargin : CGFloat = 10
    var buttons : [CheckBoxButton] = []
    var boxNames : [String] = []
    var buttonColor = UIColor()
    
    /**
    Sets up the checklist view
    
    - parameter checkboxNames: an array with the names of the checkboxes
    - parameter isExclusive:   whether or not only one box can be checked at a time
    */
    
    open func setColor(_ theColor : UIColor){
        buttonColor = theColor
        for aButton in buttons{
            aButton.setColor(theColor)
        }
    }
    
    open func setShape(_ shape : String){
        for aButton in buttons{
            aButton.setShape(shape)
        }
    }
    
    open func checkboxSetup(_ checkboxNames: [String], font: UIFont, color : UIColor, isExclusive : Bool){
        self.backgroundColor = UIColor(white: 1, alpha: 0.65)
        self.layer.cornerRadius = 6
        buttons = []
        boxNames = checkboxNames
        buttonColor = color
        var yIndex : CGFloat = boxMargin
        for aName in checkboxNames{
            let curCheckBox = CheckBoxButton()
            curCheckBox.buttonColor = buttonColor
            curCheckBox.buttonFont = font
            self.addSubview(curCheckBox)
            curCheckBox.setName(aName)
            curCheckBox.frame.origin = CGPoint(x: boxMargin, y: yIndex)
            yIndex += curCheckBox.frame.height + 2 * boxMargin
            buttons.append(curCheckBox)
            if (isExclusive == true){
                curCheckBox.removeTarget(curCheckBox, action: #selector(CheckBoxButton.toggle(_:)), for: .touchUpInside)
                curCheckBox.addTarget(self, action: #selector(ChecklistView.exclusiveToggle(_:)), for: .touchUpInside)
                exclusiveToggle(buttons[0])
            }
            
            
        }
        height = yIndex
        
    }
    
    
    /**
    For a list where only on thing can be checked at a time, this method is added to the buttons
    
    - parameter sender: the button pressed
    */
    func exclusiveToggle(_ sender: AnyObject){
        for aButton in buttons{
            aButton.turnOff()
        }
        let something = sender as! CheckBoxButton
        something.turnOn()
    }
    
    /**
    Returns an array of strings indicating what has been checked
    
    - returns: an array of strings showing what has been checked
    */
    open func getOutput() -> [String]{
        var i : Int = 0
        var outputArray : [String] = []
        for aName in boxNames{
            if buttons[i].isOn == true{
                outputArray.append(aName)
            }
            i += 1
        }
        
        return outputArray
        
        
    }
    

}
