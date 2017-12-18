//
//  UIView+Extensions.swift
//  CoolCards
//
//  Created by Chatterjee, Sumeru(AWF) on 12/18/17.
//  Copyright © 2017 Chatterjee, Sumeru. All rights reserved.
//

import UIKit

protocol ReusableView: class {
    
    static var DefaultReuseIdentifier: String { get }
}

extension ReusableView where Self: UIView {
    
    static var DefaultReuseIdentifier: String { return NSStringFromClass(self) }
}

protocol NibLoadableView: class {
    
    static var nibName: String { get }
}

extension NibLoadableView where Self: UIView {
    
    static var nibName: String {
        return NSStringFromClass(self).components(separatedBy: ".").last!
    }
}

