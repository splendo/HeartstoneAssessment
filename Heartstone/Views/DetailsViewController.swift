//
//  DetailsViewController.swift
//  Heartstone
//
//  Created by Grigory Avdyushin on 04/07/2019.
//  Copyright © 2019 Grigory Avdyushin. All rights reserved.
//

import UIKit

protocol DetailsViewDelegate: class {
    func onDetailsViewFinished(_ viewController: UIViewController)
    func onToggleFavorite(_ viewController: UIViewController)
}

class DetailsViewController: UIViewController {

    @IBOutlet weak var coverImage: UIImageView!
    @IBOutlet weak var mainTitle: UILabel!
    @IBOutlet weak var subTitle: UILabel!
    @IBOutlet weak var favoriteButton: UIButton!

    weak var delegate: DetailsViewDelegate?

    var viewModel: DetailsViewModel? {
        didSet {
            loadViewIfNeeded()
            if let imageUrl = viewModel?.imageURL {
                coverImage.kf.setImage(with: imageUrl)
            }
            mainTitle.text = viewModel?.name
            subTitle.text = viewModel?.text
            UIView.performWithoutAnimation {
                favoriteButton.setTitle(viewModel?.favoriteButtonTitle, for: [.normal])
                favoriteButton.layoutIfNeeded()
            }
        }
    }

    override func viewDidLoad() {
        super.viewDidLoad()
    }

    override func didMove(toParent parent: UIViewController?) {
        super.didMove(toParent: parent)
        if parent == nil {
            delegate?.onDetailsViewFinished(self)
        }
    }

    @IBAction func onFavoriteTap(_ sender: Any) {
        delegate?.onToggleFavorite(self)
    }
}
