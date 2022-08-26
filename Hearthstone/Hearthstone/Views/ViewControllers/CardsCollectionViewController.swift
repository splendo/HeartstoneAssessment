//
//  CardsCollectionViewController.swift
//  Hearthstone
//
//  Created by Stavros Tsikinas on 29/7/22.
//

import UIKit

class CardsCollectionViewController: UICollectionViewController, UICollectionViewDelegateFlowLayout, UpdateFavoritesProtocol {
    
    // MARK: - Variables
    
    // Public
    public var dataService: CardsDataService?
    
    // Private
    private var filteredCards = [CardViewModel]()
    private var isFeatured: Bool = false
    private var hsiaoFavButton = UIBarButtonItem()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        collectionView.register(CardGridViewCell.self, forCellWithReuseIdentifier: "CardCell")
        setupBar()
        refreshData()
    }
    
    // MARK: - CollectionView Functions
    override func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        filteredCards.count
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        return CGSize(width: 150, height: 250)
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, insetForSectionAt section: Int) -> UIEdgeInsets {
        return UIEdgeInsets(top: 10, left: 10, bottom: 10, right: 10)
    }

    override func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        guard let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "CardCell", for: indexPath) as? CardGridViewCell else {
            return UICollectionViewCell()
        }
        if filteredCards.isEmpty {
            return cell
        }
        cell.cardViewModel = filteredCards[indexPath.row]
        
        return cell
    }
    
    override func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        let card = filteredCards[indexPath.row]
        card.select()
    }
    
    // MARK: - Setup Functions
    private func setupBar() {
        hsiaoFavButton = UIBarButtonItem(image: UIImage(systemName: "star.fill"), style: .plain, target: self, action: #selector(filterFeatured))
        addButtons(right: [hsiaoFavButton])
    }
    
    // MARK: - Update Functions
    private func refreshData() {
        
        DispatchQueue.global().async { [weak self] in
            self?.dataService?.convert(from: "cards") { items in
                self?.dataService?.handleParsed(items, from: self) { itemsVM in
                    self?.filteredCards = itemsVM
                    DispatchQueue.main.async {
                        if self?.filteredCards.count == 0 {
                            self?.showWatermark()
                        }
                        self?.collectionView.reloadData()
                    }
                }
            }
        }
    }
    
    // MARK: - UpdateFavoritesProtocol functions
    
    func initFavorite(for card: CardViewModel, completion: @escaping(Bool) -> Void) {
        dataService?.favoritesService?.exists(with: card.cardID) { isFavorite in
            completion(isFavorite)
        }
    }
    
    func updateFavorite(for card: CardViewModel) {
        if card.isFavorite {
            dataService?.favoritesService?.save(card.cardID) { [weak self] _ in
                DispatchQueue.main.async {
                    self?.collectionView.reloadData()
                }
            }
        } else {
            dataService?.favoritesService?.delete(cardID: card.cardID) { [weak self] _ in
                DispatchQueue.main.async {
                    self?.collectionView.reloadData()
                }
            }
        }
    }
    
    // MARK: - Obj-C Functions
    @objc func filterFeatured() {
        isFeatured = !isFeatured
        hsiaoFavButton.image = UIImage(systemName: isFeatured ? "star.slash.fill" : "star.fill")
        if isFeatured {
            filteredCards = dataService?.featuresFilter(for: filteredCards) ?? []
            collectionView.reloadData()
        } else {
            refreshData()
        }
    }
    
}
