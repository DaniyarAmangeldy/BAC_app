//
//  NewsCell.swift
//  Bas App
//
//  Created by Fxz Zz on 4/11/16.
//  Copyright © 2016 Fxz Zz. All rights reserved.
//

import UIKit

class NewsCell: UICollectionViewCell {

    @IBOutlet weak var imageView: UIImageView!
    @IBOutlet weak var textLabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        super.awakeFromNib()
        self.layer.cornerRadius = 5.0
        self.layer.borderColor = UIColor.blackColor().CGColor
        self.layer.borderWidth = 0.3
    }

}
