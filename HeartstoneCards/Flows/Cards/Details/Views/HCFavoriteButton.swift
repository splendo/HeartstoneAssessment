//
// Created by Roman Petryshen on 26/04/2018.
// Copyright (c) 2018 Roman Petryshen. All rights reserved.
//

import Foundation
import UIKit

class HCFavoriteButton: UIButton {

    var isFavorite = false {
        didSet {
            updateView()
        }
    }

    let imageSelected: String
    let imageUnselected: String

    required init?(coder aDecoder: NSCoder) {
        fatalError("Not implemented")
    }

    required init(imageSelected: String = "StarIcon_Selected", imageUnselected: String = "StarIcon_Unselected") {
        self.imageSelected = imageSelected
        self.imageUnselected = imageUnselected
        super.init(frame: .zero)
        updateView()
    }

    private func updateView() {
        let imageName = isFavorite ? imageSelected : imageUnselected
        guard let image = UIImage(named: imageName) else {
            fatalError("Image \(imageName) can't be loaded")
        }
        setImage(image, for: .normal)
    }
}
