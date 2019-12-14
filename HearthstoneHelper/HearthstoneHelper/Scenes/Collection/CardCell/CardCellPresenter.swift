//
// Created by Maxim Berezhnoy on 14/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

import struct Foundation.Data
import class UIKit.UIImage

protocol CardCellPresenting {
    func present(name: String)
    func presentImage(from data: Data)
    func presentLoading()
    func presentErrorImage()
    func presentPlaceholderImage()
}

class CardCellPresenter: CardCellPresenting {
    weak var view: CardCellDisplaying?
    
    init(view: CardCellDisplaying) {
        self.view = view
    }

    func present(name: String) {
        view?.display(name: name)
    }

    func presentImage(from data: Data) {
        guard let image = UIImage(data: data) else {
            presentErrorImage()
            return 
        }
        
        view?.display(image: image)
    }

    func presentLoading() {
        
    }

    func presentErrorImage() {
        
    }

    func presentPlaceholderImage() {
        
    }
}