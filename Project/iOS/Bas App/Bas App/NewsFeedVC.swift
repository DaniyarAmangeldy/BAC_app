//
//  NewsFeedVC.swift
//  Bas App
//
//  Created by Fxz Zz on 3/26/16.
//  Copyright © 2016 Fxz Zz. All rights reserved.
//

import UIKit
import Alamofire
import Haneke
import SwiftyJSON

class NewsFeedVC: UIViewController, UICollectionViewDelegate, UICollectionViewDataSource {

    var layouter: BackingCellLayouter<NewsCell>!
    let cellID = "cell"
    
    var images = [UIImage]()
    
    var arrRes = [[String:AnyObject]]() //Array of dictionary
    var url = "https://api.instagram.com/v1/users/482993112/media/recent/?access_token=2253563781.137bf98.bd1c3693d2b84f80a7ab8d661f641437&scount=20"


    @IBOutlet weak var collectionView: UICollectionView!
    
    var refreshControl:UIRefreshControl!

    
    override func viewDidLoad() {
        super.viewDidLoad()
        navigationController!.tabBarItem.selectedImage = UIImage(named: "news_feed_amber")!.imageWithRenderingMode(.AlwaysOriginal)
        
        self.collectionView!.alwaysBounceVertical = true
        let refresher = UIRefreshControl()
        refresher.addTarget(self, action: #selector(NewsFeedVC.refreshStream), forControlEvents: .ValueChanged)
        
        refreshControl = refresher
        collectionView!.addSubview(refreshControl!)
        
        
        self.automaticallyAdjustsScrollViewInsets = true
        self.edgesForExtendedLayout = .None
        
        self.collectionView.delegate = self
        self.collectionView.dataSource = self
        
        let cellNib = UINib(nibName: "NewsCell", bundle: nil)
        self.collectionView.registerNib(cellNib, forCellWithReuseIdentifier: cellID)
        
        self.collectionView.backgroundColor = UIColor.whiteColor()
        // init the layouter
        let cell = cellNib.instantiateWithOwner(nil, options: nil).first as! NewsCell
        self.layouter = BackingCellLayouter<NewsCell>(cell: cell, setupCallback: { [weak self] (cell: NewsCell, indexPath: NSIndexPath) in
            self?.setupCell(cell, indexPath: indexPath)
            })
        
        loadInstagramJSON()
        
    }
    
    func refreshStream() {
        print("refresh")
        self.collectionView?.reloadData()
        refreshControl?.endRefreshing()
    }
    
    /*override func preferredStatusBarStyle() -> UIStatusBarStyle {
        return .LightContent
    }
    */
    
    func collectionView(collectionView: UICollectionView, cellForItemAtIndexPath indexPath: NSIndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCellWithReuseIdentifier(cellID, forIndexPath: indexPath) as! NewsCell
        self.setupCell(cell, indexPath: indexPath)
        return cell
    }
    
    func collectionView(collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAtIndexPath indexPath: NSIndexPath) -> CGSize{
        return self.layouter.sizeForCellAtIndexPath(indexPath)
    }
    
    func collectionView(collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return self.arrRes.count
    }
    
    func setupCell(cell: NewsCell, indexPath: NSIndexPath){
        
        var dict = arrRes[indexPath.row]
        cell.textLabel.text = dict["caption"]!["text"] as? String
        
        if let imgUrl = dict["images"]!["standard_resolution"]!!["url"] as? String {
            let ns_url = NSURL(string: imgUrl)
            cell.imageView.hnk_setImageFromURL(ns_url!)
        }

        
    }
    
    func loadInstagramJSON() {
        Alamofire.request(.GET, url).responseJSON { (responseData) -> Void in
            if((responseData.result.value) != nil) {
                let swiftyJsonVar = JSON(responseData.result.value!)
                
                if let resData = swiftyJsonVar["data"].arrayObject {
                    self.arrRes = resData as! [[String:AnyObject]]
                }
                if self.arrRes.count > 0 {
                    self.collectionView.reloadData()
                }
            }
        }

    }
    
    
    
    
   
   
}
