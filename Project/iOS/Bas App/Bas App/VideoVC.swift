//
//  VideoVC.swift
//  Bas App
//
//  Created by Fxz Zz on 3/26/16.
//  Copyright © 2016 Fxz Zz. All rights reserved.
//

import UIKit

class VideoVC: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        tabBarItem.selectedImage = UIImage(named: "video_amber")!.imageWithRenderingMode(.AlwaysOriginal)

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
