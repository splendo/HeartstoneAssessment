//
//  BaseFlowCollectionViewController.swift
//  CoolCards
//
//  Created by Chatterjee, Sumeru(AWF) on 12/18/17.
//  Copyright © 2017 Chatterjee, Sumeru. All rights reserved.
//

import UIKit

let kImagePosterRatio: CGFloat = (2.0 / 3.0)

public class BaseFlowCollectionViewController: UIViewController {
    
    @IBOutlet weak var collectionView: UICollectionView!
    
    public override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.collectionViewItemsPerRow = self.itemsPerRow(for: UIScreen.main.bounds.size)
    }
    
    public override func viewWillTransition(to size: CGSize, with coordinator: UIViewControllerTransitionCoordinator) {
        super.viewWillTransition(to: size, with: coordinator)
        guard self.isViewLoaded else { return }
        self.collectionViewItemsPerRow = self.itemsPerRow(for: size)
        self.collectionView.collectionViewLayout.invalidateLayout()
    }
    
    fileprivate func itemsPerRow(for size: CGSize) -> Int {
        if size.width > 768 { return 4 }
        else if size.width > 414 { return 3 }
        else { return 2 }
    }
    
    fileprivate var collectionViewItemsPerRow: Int = 2
    fileprivate let collectionViewMargin: CGFloat = 15.0
    fileprivate let collectionViewItemSizeRatio: CGFloat = kImagePosterRatio
    fileprivate var collectionViewItemWidth: CGFloat {
        return (self.collectionView.bounds.width - (CGFloat(self.collectionViewItemsPerRow + 1) * self.collectionViewMargin)) / CGFloat(self.collectionViewItemsPerRow)
    }
    fileprivate var collectionViewItemHeight: CGFloat {
        return self.collectionViewItemWidth / self.collectionViewItemSizeRatio
    }
}

extension BaseFlowCollectionViewController: UICollectionViewDelegateFlowLayout {
    
    public func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        collectionView.deselectItem(at: indexPath, animated: true)
    }
    
    public func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        return CGSize(width: self.collectionViewItemWidth, height: self.collectionViewItemHeight)
    }
    
    public func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, insetForSectionAt section: Int) -> UIEdgeInsets {
        return UIEdgeInsets(top: self.collectionViewMargin, left: self.collectionViewMargin, bottom: self.collectionViewMargin, right: self.collectionViewMargin)
    }
    
    public func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumLineSpacingForSectionAt section: Int) -> CGFloat {
        return self.collectionViewMargin
    }
}

