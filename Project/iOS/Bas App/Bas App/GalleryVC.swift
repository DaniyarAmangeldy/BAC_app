//
//  GalleryVC.swift
//  Bas App
//
//  Created by Fxz Zz on 3/26/16.
//  Copyright © 2016 Fxz Zz. All rights reserved.
//

import UIKit

class GalleryVC: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        navigationController!.tabBarItem.selectedImage = UIImage(named: "gallery_amber")!.imageWithRenderingMode(.AlwaysOriginal)

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    
}
