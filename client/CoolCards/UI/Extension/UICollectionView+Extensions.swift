//
//  UICollectionView+Extensions.swift
//  CoolCards
//
//  Created by Chatterjee, Sumeru(AWF) on 12/18/17.
//  Copyright © 2017 Chatterjee, Sumeru. All rights reserved.
//

import UIKit

extension UICollectionView {
    
    func registerReusableCell<T: UICollectionViewCell>(_: T.Type) where T: ReusableView {
        self.register(T.self, forCellWithReuseIdentifier: T.DefaultReuseIdentifier)
    }
    
    func registerReusableCell<T: UICollectionViewCell>(_: T.Type) where T: ReusableView, T: NibLoadableView {
        let bundle = Bundle(for: T.self)
        let nib = UINib(nibName: T.nibName, bundle: bundle)
        self.register(nib, forCellWithReuseIdentifier: T.DefaultReuseIdentifier)
    }
    
    func dequeueReusableCell<T: UICollectionViewCell>(indexPath: IndexPath) -> T where T: ReusableView {
        guard let cell = dequeueReusableCell(withReuseIdentifier: T.DefaultReuseIdentifier, for: indexPath) as? T else {
            fatalError("Could not dequeue cell with identifier: \(T.DefaultReuseIdentifier)")
        }
        return cell
    }
}
